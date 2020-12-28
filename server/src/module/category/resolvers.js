import CategoryModel from './category';

const categories = {
    name: 'categories',
    type: ['Category!'],
    resolve: async () => {
        const categoriesList = await CategoryModel.find();
        return categoriesList;
    } 
};

const addCategory = {
    name: 'addCategory',
    type: 'String!',
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

            return 'succeed: true';
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
