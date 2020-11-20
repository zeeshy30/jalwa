import UserValidator from './userValidator';
import ProductValidator from './productValidator';

const UserValidatorObject = UserValidator.getInstance();
export const verifyCodeValidator = UserValidatorObject.verifyCode;
export const requestCodeValidator = UserValidatorObject.requestCode;
export const updateUserValidator = UserValidatorObject.updateUser;

const ProductValidatorObject = ProductValidator.getInstance();
export const addProductValidator = ProductValidatorObject.addProduct;
