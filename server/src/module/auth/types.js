import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import UserModel from './user';
import { error } from '../../graphql/types';

const UserTC = composeWithMongoose(UserModel);

const userAccountTC = UserTC.getFieldTC('account');

userAccountTC.getFieldTC('verification')
    .removeField(['token']);

const accessToken = schemaComposer.createObjectTC({
    name: 'AccessToken',
    fields: { 
        accessToken: 'String' 
    }
});

schemaComposer.createUnionTC({
    name: 'UserResult',
    types: [ error, userAccountTC ],
});

schemaComposer.createUnionTC({
    name: 'SignIn',
    types: [error, accessToken]
});

export default userAccountTC;
