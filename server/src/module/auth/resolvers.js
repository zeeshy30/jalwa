import jwt from 'jsonwebtoken';
import twilio from 'twilio';

import UserModel from './user';


const twilioClient = twilio('AC19caf0660254fcea9c02d0667433fa23', '588ff60f38f579a293a070fccf2feaa1');

const sendVerificationCode = async (phoneNumber) => {
    try { 
        await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
            .verifications
            .create({to: phoneNumber, channel: 'sms'});
    }
    catch(err) {
        throw new Error('Error Sending Verification Code!');
    }
};

const verifyCode = async (phoneNumber, code) => {
    try {
        await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
            .verificationChecks
            .create({ to: phoneNumber, code });
    }
    catch(err) {
        throw new Error('Error Verifying Code!');
    }
};

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
                return Promise.reject(new Error('User not found.'));
            }

            const res = await verifyCode(phoneNumber, code);
            if (res.status !== 'approved') {
                return Promise.reject(new Error('Invalid code provided'));
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

            return { accessToken };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const requestCode = {
    name: 'requestCode',
    type: 'String!',
    args: {
        phoneNumber: 'String!',
    },
    resolve: async ({ args: { phoneNumber } }) => {
        try {
            let user = await UserModel.numberExist(phoneNumber);
            await sendVerificationCode(phoneNumber);

            if (!user) {
                user = await new UserModel({
                    phoneNumber,
                }).save();
            }

            return 'success!';
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const signUp = {
    name: 'signUp',
    type: 'AccessToken!',
    args: {
        phoneNumber: 'String!',
        code: 'String!'
    },
    resolve: async ({ args: { phoneNumber, code } }) => {
        try {
            const user = await UserModel.numberExist(phoneNumber);
            if (user && user.account.verification.verified) {
                return Promise.reject(new Error('This number has already been registered.'));
            }

            const res = await verifyCode(phoneNumber, code);
            if (res.status !== 'approved') {
                return Promise.reject(new Error('Invalid code provided'));
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

            return { accessToken };
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

const logout = {
    name: 'logout',
    type: 'Succeed!',
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
        return { succeed: true };
    }
};


const updateUser = {
    name: 'updateUser',
    type: 'User!',
    args: { fullName: 'String!' },
    resolve: async ({ args: { fullName }, context: { user } }) => {
        try {
            user.set({
                fullName
            });

            await user.save();

            return user;
        } catch (error) {
            return Promise.reject(error);
        }
    }
};

export default {
    userObject,
    signIn,
    signUp,
    requestCode,
    logout,
    updateUser,
};
