import CategoryModel from './category';
import status from '../statusSchema';

const categories = {
    name: 'categories',
    type: 'CategoriesResult!',
    resolve: async () => {
        const categoriesList = await CategoryModel.find();
        const result = {
            status: {
                statusCode: 200,
                message: 'Success',
            },
            result: categoriesList
        };
        return result;
    } 
};

const addCategory = {
    name: 'addCategory',
    type: 'status!',
    args: {
        category: 'String!',
    },
    resolve: async ({ args }) => {
        const { category } = args;
        try {
            const categoryExists = await CategoryModel.findOne({ category });
            if (categoryExists) {
                return Promise.reject(new Error('This category already exists.'));
            }

            await new CategoryModel({
                category
            }).save();

            return {
                statusCode: 200,
                message: 'Success'
            };
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};


export default {
    categories,
    addCategory
};
