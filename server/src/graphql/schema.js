import { schemaComposer } from 'graphql-compose';
import './types';

import { isAuth, isGuest, isUnverfied, isVerified } from '../middleware';

import {
    requestCodeValidator,
    verifyCodeValidator,
    updateUserValidator,
} from '../validator';

import UserTC from '../module';

schemaComposer.Query.addFields({
    user: UserTC.getResolver('user', [isAuth])
});

schemaComposer.Mutation.addFields({
    signIn: UserTC.getResolver('signIn', [isGuest, verifyCodeValidator]),
    signUp: UserTC.getResolver('signUp', [isGuest, verifyCodeValidator]),
    requestCode: UserTC.getResolver('requestCode', [requestCodeValidator]),
    logout: UserTC.getResolver('logout', [isAuth]),
    updateUser: UserTC.getResolver('updateUser', [isAuth, updateUserValidator]),
});

const schema = schemaComposer.buildSchema();

export default schema;
