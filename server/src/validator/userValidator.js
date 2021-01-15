import validator from 'validator';

class UserValidator {
    requestCode = async (resolve, source, args, context, info) => {
        let { phoneNumber } = args;

        phoneNumber = validator.trim(phoneNumber);
        Object.assign(args, { phoneNumber });

        if (!validator.isMobilePhone(phoneNumber)) {
            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Invalid Phone Number!'
            };
        }

        return resolve(source, args, context, info);
    }

    verifyCode = async (resolve, source, args, context, info) => {
        let { phoneNumber } = args;

        phoneNumber = validator.trim(phoneNumber);

        Object.assign(args, { phoneNumber });

        const { code } = args;
        if (!validator.isMobilePhone(phoneNumber)) {
            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Invalid Phone Number!'
            };
        }

        if (!validator.isLength(code, { min: 4, max: 4 })) {
            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Code must be 4 digits long!'
            };
        }

        return resolve(source, args, context, info);
    }

    updateUser = async (resolve, source, args, context, info) => {

        const { fullName } = args;
        if (!validator.isLength(fullName, { min: 2 })) {
            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Name must be at least 2 character long'
            };
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
