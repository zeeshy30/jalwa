import jwt from 'jsonwebtoken';

import UserModel from './user';
import { sendVerificationCodeService, verifyCodeService } from './service/index';
// const twilioClient = twilio('AC19caf0660254fcea9c02d0667433fa23', '588ff60f38f579a293a070fccf2feaa1');

// const sendVerificationCode = async (phoneNumber) => {
//     try { 
//         await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
//             .verifications
//             .create({to: phoneNumber, channel: 'sms'});
//     }
//     catch(err) {
//         throw new Error('Error Sending Verification Code!');
//     }
// };

// const verifyCode = async (phoneNumber, code) => {
//     try {
//         await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
//             .verificationChecks
//             .create({ to: phoneNumber, code });
//     }
//     catch(err) {
//         throw new Error('Error Verifying Code!');
//     }
// };

const userObject = {
    name: 'user',
    type: 'User!',
    resolve: ({ context: { user } }) => user
};

const signIn = {
    name: 'signIn',
    type: 'AccessToken!',
    args: {
        phoneNumber: 'String!',
        code: 'String!'
    },
    resolve: async ({ args: { phoneNumber, code } }) => {
        try {
            const user = await UserModel.numberExist(phoneNumber);
            if (!user) {
                return {
                    status: {
                        statusCode: 404,
                        message: 'Phone number not registered'
                    }
                };
            }

            const res = await verifyCodeService(phoneNumber, code);
            if (res.status !== 'approved') {
                return {
                    status: {
                        statusCode: 410,
                        message: 'Invalid Verification Code'
                    }
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
                status: {
                    statusCode: 200,
                    message: 'Success'
                },
                accessToken
            };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const requestCode = {
    name: 'requestCode',
    type: 'status!',
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
                statusCode: 200,
                message: 'Success'
            };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const logout = {
    name: 'logout',
    type: 'status!',
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
            statusCode: 200,
            message: 'Success'
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
            const result = {
                status: {
                    statusCode: 200,
                    message: 'Success',
                },
                result: user
            };
            return result;
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
