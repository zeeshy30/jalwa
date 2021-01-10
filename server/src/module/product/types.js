import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import status from '../statusSchema';
import ProductModel from './product';

const ProductTC = composeWithMongoose(ProductModel);

const productTCResult = schemaComposer.createObjectTC({
    name: 'ProductResult',
    fields: { 
        status,
        result: [ProductTC]
    }
});

export default productTCResult;
