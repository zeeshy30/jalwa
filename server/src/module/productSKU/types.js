import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import ProductSKUModel from './productSKU';

const ProductSKUTC = composeWithMongoose(ProductSKUModel);

// schemaComposer.createObjectTC({
//     name: 'AccessToken',
//     fields: { accessToken: 'String!' }
// });

export default ProductSKUTC;
