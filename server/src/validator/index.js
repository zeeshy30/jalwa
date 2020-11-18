import UserValidator from './userValidator';

const UserValidatorObject = UserValidator.getInstance();
export const verifyCodeValidator = UserValidatorObject.verifyCode;
export const requestCodeValidator = UserValidatorObject.requestCode;
export const updateUserValidator = UserValidatorObject.updateUser;
