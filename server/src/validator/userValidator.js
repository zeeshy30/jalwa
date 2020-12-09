import validator from 'validator';

class UserValidator {
    requestCode = async (resolve, source, args, context, info) => {
        let { phoneNumber } = args;

        phoneNumber = validator.trim(phoneNumber);
        Object.assign(args, { phoneNumber });

        if (!validator.isMobilePhone(phoneNumber)) {
            return Promise.reject(new Error('Error: phoneNumber'));
        }

        return resolve(source, args, context, info);
    }

    verifyCode = async (resolve, source, args, context, info) => {
        let { phoneNumber } = args;

        phoneNumber = validator.trim(phoneNumber);

        Object.assign(args, { phoneNumber });

        const { code } = args;

        if (!validator.isMobilePhone(phoneNumber)) {
            return Promise.reject(new Error('Error: phoneNumber'));
        }

        if (!validator.isLength(code, { min: 4, max: 4 })) {
            return Promise.reject(new Error('Error: code'));
        }

        return resolve(source, args, context, info);
    }

    updateUser = async (resolve, source, args, context, info) => {

        const { fullName } = args;
        if (!validator.isLength(fullName, { min: 2 })) {
            return Promise.reject(new Error('Error: fullName'));
        }

        return resolve(source, args, context, info);
    }

    static getInstance () {
        if (!this.instance) {
            this.instance = new this();
        }
        return this.instance;
    }
}

export default UserValidator;
