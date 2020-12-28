import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import CategoryModel from './category';

const CategoryTC = composeWithMongoose(CategoryModel);

// schemaComposer.createObjectTC({
//     name: 'AccessToken',
//     fields: { accessToken: 'String!' }
// });

export default CategoryTC;
