import { schemaComposer } from 'graphql-compose';
import './types';

import { isAuth, isGuest, isUnverfied, isVerified } from '../middleware';

import {
    requestCodeValidator,
    verifyCodeValidator,
    updateUserValidator,

    addProductValidator,

    addProductSKUValidator
} from '../validator';

import TC from '../module';

schemaComposer.Query.addFields({
    user: TC.UserTC.getResolver('user', [isAuth]),

    products: TC.ProductTC.getResolver('products'),

    productSKUs: TC.ProductSKUTC.getResolver('productSKUs'),
});

schemaComposer.Mutation.addFields({
    signIn: TC.UserTC.getResolver('signIn', [isGuest, verifyCodeValidator]),
    signUp: TC.UserTC.getResolver('signUp', [isGuest, verifyCodeValidator]),
    requestCode: TC.UserTC.getResolver('requestCode', [requestCodeValidator]),
    logout: TC.UserTC.getResolver('logout', [isAuth]),
    updateUser: TC.UserTC.getResolver('updateUser', [isAuth, updateUserValidator]),

    addProduct: TC.ProductTC.getResolver('addProduct', [addProductValidator]),

    addProductSKU: TC.ProductSKUTC.getResolver('addProductSKU', [addProductSKUValidator]),
});

const schema = schemaComposer.buildSchema();

export default schema;
