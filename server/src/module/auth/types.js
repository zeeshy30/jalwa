import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import UserModel from './user';
import status from '../statusSchema';

const UserTC = composeWithMongoose(UserModel);

const userAccountTC = UserTC.getFieldTC('account');

userAccountTC.getFieldTC('verification')
    .removeField(['token']);

schemaComposer.createObjectTC({
    name: 'AccessToken',
    fields: { 
        status,
        accessToken: 'String' 
    }
});

const UserResult = schemaComposer.createObjectTC({
    name: 'UserResult',
    fields: { 
        status,
        result: userAccountTC
    }
});

export default UserResult;
