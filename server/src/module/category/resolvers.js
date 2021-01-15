import CategoryModel from './category';

const categories = {
    name: 'categories',
    type: 'CategoriesResult!',
    resolve: async () => {
        try {
            const categoriesList = await CategoryModel.find();
            return {
                __typename: 'Categories',
                categories: categoriesList
            };
        } catch(e) {
            return Promise.reject(e);
        }
    } 
};

const addCategory = {
    name: 'addCategory',
    type: 'Status!',
    args: {
        category: 'String!',
    },
    resolve: async ({ args }) => {
        const { category } = args;
        try {
            const categoryExists = await CategoryModel.findOne({ category });
            if (categoryExists) {
                return  {
                    __typename: 'Error',
                    statusCode: 409,
                    message: 'This category already exists.'
                };
            }

            await new CategoryModel({
                category
            }).save();

            return {
                __typename: 'Succeed',
                succeed: true
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
