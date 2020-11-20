import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import ProductModel from './product';

const ProductTC = composeWithMongoose(ProductModel);

// schemaComposer.createObjectTC({
//     name: 'AccessToken',
//     fields: { accessToken: 'String!' }
// });

export default ProductTC;
