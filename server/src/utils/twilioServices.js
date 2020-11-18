import twilio from 'twilio';

const twilioClient = twilio('AC19caf0660254fcea9c02d0667433fa23', '588ff60f38f579a293a070fccf2feaa1');

export const sendVerificationCode = async (phoneNumber) => {
    try { 
        return await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
            .verifications
            .create({to: phoneNumber, channel: 'sms'});
    }
    catch(err) {
        Promise.Reject(err);
        return err;

    }
};

export const verifyCode = async (phoneNumber, code) => {
    try {
        return await twilioClient.verify.services('VAbf29bba1e0990cecb48079b88d422b1d')
            .verificationChecks
            .create({ to: phoneNumber, code });
    }
    catch(err) {
        Promise.reject(err);
        return err;
    }
        
};
