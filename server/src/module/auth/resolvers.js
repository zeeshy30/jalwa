import jwt from 'jsonwebtoken';

import UserModel from './user';
import { sendVerificationCodeService, verifyCodeService } from './service/index';

const userObject = {
    name: 'user',
    type: 'UserResult!',
    resolve: ({ context: { user } }) => {
        return {
            typename: 'User',
            user
        };
    }
};

const signIn = {
    name: 'signIn',
    type: 'SignIn!',
    args: {
        phoneNumber: 'String!',
        code: 'String!'
    },
    resolve: async ({ args: { phoneNumber, code } }) => {
        try {
            const user = await UserModel.numberExist(phoneNumber);
            if (!user) {
                return  {
                    __typename: 'Error',
                    statusCode: 404,
                    message: 'Phone number not registered'
                };
            }

            const res = await verifyCodeService(phoneNumber, code);
            if (res.status !== 'approved') {
                return {
                    __typename: 'Error',
                    statusCode: 410,
                    message: 'Invalid Verification Code'
                };
            }

            const accessToken = jwt.sign(
                { userId: user._id },
                process.env.JWT_SECRET,
            );

            user.account = {
                verification: {
                    verified: true,
                    token: accessToken,
                },
            };
            user.save();

            return {
                __typename: 'AccessToken',
                accessToken
            };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const requestCode = {
    name: 'requestCode',
    type: 'Status!',
    args: {
        phoneNumber: 'String!',
    },
    resolve: async ({ args: { phoneNumber } }) => {
        try {
            let user = await UserModel.numberExist(phoneNumber);
            await sendVerificationCodeService(phoneNumber);

            if (!user) {
                user = await new UserModel({
                    phoneNumber,
                }).save();
            }

            return {
                __typename: 'Succeed',
                succeed: true,
            };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const logout = {
    name: 'logout',
    type: 'Status!',
    resolve: async ({ context: { user, accessToken } }) => {
        // try {
        //     await redis.set(
        //         `expiredToken:${accessToken}`,
        //         user._id,
        //         'EX',
        //         process.env.REDIS_TOKEN_EXPIRY
        //     );

        //     return { succeed: true };
        // } catch (error) {
        //     return Promise.reject(error);
        // }
        return {
            succeed: true
        };
    }
};


const updateUser = {
    name: 'updateUser',
    type: 'UserResult!',
    args: { fullName: 'String!' },
    resolve: async ({ args: { fullName }, context: { user } }) => {
        try {
            user.set({
                fullName
            });

            await user.save();
            return {
                __typename: 'User',
                user
            };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

export default {
    userObject,
    signIn,
    requestCode,
    logout,
    updateUser,
};
