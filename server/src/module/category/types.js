import { schemaComposer } from 'graphql-compose';
import { composeWithMongoose } from 'graphql-compose-mongoose';

import status from '../statusSchema';
import CategoryModel from './category';

const CategoryTC = composeWithMongoose(CategoryModel);

const CategoriesResult = schemaComposer.createObjectTC({
    name: 'CategoriesResult',
    fields: { 
        status,
        result: [CategoryTC]
    }
});

export default CategoriesResult;
