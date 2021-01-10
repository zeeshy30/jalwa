import crypto from 'crypto-random-string';
import moment from 'moment';
import twilio from 'twilio';

const twilioClient = twilio('AC19caf0660254fcea9c02d0667433fa23', '588ff60f38f579a293a070fccf2feaa1');

class UserService {
    verifyRequest = async (user) => {
        const token = crypto({ length: 48, type: 'url-safe' });
        const expiresIn = moment().add(100, 'days');

        user.set({
            account: {
                verification: {
                    token,
                    expiresIn
                }
            }
        });

        await user.save();

        return token;
    }

    sendVerificationCode = async (phoneNumber) => {
        try { 
            await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
                .verifications
                .create({to: phoneNumber, channel: 'sms'});
        }
        catch(err) {
            throw new Error('Error Sending Verification Code!');
        }
    };
    
    verifyCode = async (phoneNumber, code) => {
        try {
            await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
                .verificationChecks
                .create({ to: phoneNumber, code });
        }
        catch(err) {
            throw new Error('Error Verifying Code!');
        }
    };

    static getInstance () {
        if (!this.instance) {
            this.instance = new this();
        }
        return this.instance;
    }
}

export default UserService;
