import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import UserModel from './user';

const UserTC = composeWithMongoose(UserModel);

const userAccountTC = UserTC.getFieldTC('account');

userAccountTC.getFieldTC('verification')
    .removeField(['token']);

schemaComposer.createObjectTC({
    name: 'AccessToken',
    fields: { accessToken: 'String!' }
});

export default UserTC;
