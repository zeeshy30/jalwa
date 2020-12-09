import UserValidator from './userValidator';
import ProductValidator from './productValidator';
import ProductSKUValidator from './productSKUValidator';

const UserValidatorObject = UserValidator.getInstance();
export const verifyCodeValidator = UserValidatorObject.verifyCode;
export const requestCodeValidator = UserValidatorObject.requestCode;
export const updateUserValidator = UserValidatorObject.updateUser;

const ProductValidatorObject = ProductValidator.getInstance();
export const addProductValidator = ProductValidatorObject.addProduct;

const ProductSKUValidatorObject = ProductSKUValidator.getInstance();
export const addProductSKUValidator = ProductSKUValidatorObject.addProductSKU;
