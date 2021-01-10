import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import status from '../statusSchema';
import ProductSKUModel from './productSKU';

const ProductSKUTC = composeWithMongoose(ProductSKUModel);

const productSKUTCResult = schemaComposer.createObjectTC({
    name: 'ProductSKUResult',
    fields: { 
        status,
        result: [ProductSKUTC]
    }
});

export default productSKUTCResult;
