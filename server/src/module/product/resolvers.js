import merge from 'deepmerge';

import ProductModel from './product';
import CategoryModel from '../category/category';

const productsFilteredByCategory = {
    name: 'productsFilteredByCategory',
    type: 'ProductResult!',
    args: {
        category: 'String!'
    },
    resolve: async ({ args }) => {
        try {
            const { category } = args;
            let productList = null;
            if (category === 'All') {
                productList = await ProductModel.find({});
            } else {
                productList = await ProductModel.find({ category });
            }
            
            return {
                status: {
                    statusCode: 200,
                    message: 'Success',
                },
                result: productList
            };
        } catch (error) {
            return Promise.reject(error);
        }
        
    } 
};

const addProduct = {
    name: 'addProduct',
    type: 'status!',
    args: {
        handle: 'String!',
        title: 'String!',
        body: 'String',
        vendor: 'String!',
        category: 'String!',
        tags: ['String'],
        price: 'String!',
        photoUrl: 'String!',
        videoUrl: 'String!',
    },
    resolve: async ({ args }) => {
        const { handle, title, body = '', vendor, category, tags = [], price, photoUrl, videoUrl } = args;
        try {
            const handleExists = await ProductModel.findOne({ handle });
            if (handleExists) {
                return {
                    statusCode: 409,
                    message: `A product with handle, ${handle} already exists`
                };
            }

            await new ProductModel({
                handle, 
                title,
                body,
                vendor, 
                category,
                tags,
                price,
                photoUrl,
                videoUrl, 
            }).save();
            
            const categoryExists = await CategoryModel.findOne({ category });

            if (!categoryExists) {
                await new CategoryModel({
                    category
                }).save();
            } else {
                return {
                    statusCode: 409,
                    message: `${category} already exists!`
                };
            }

            return {
                statusCode: 200,
                message: 'Success',
            };
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};

const updateProduct = {
    name: 'updateProduct',
    type: 'status!',
    args: {
        _id: 'String!',
        title: 'String',
        body: 'String',
        vendor: 'String',
        category: 'String',
        tags: ['String'],
        price: 'String',
        photoUrl: 'String',
        videoUrl: 'String',
    },
    resolve: async ({ args }) => {
        const { _id } = args;
        try {
            const product = await ProductModel.findById(_id);
            if (!product) {
                return {
                    statusCode: 404,
                    message: 'Product Not Found'
                };
            }

            Object.assign(product, merge(product.toObject(), args));
            await product.save();
            return {
                statusCode: 200,
                message: 'Success',
            };
        }
        catch(error) {
            return Promise.reject(error);   
        }
    }
};

export default {
    productsFilteredByCategory,
    addProduct,
    updateProduct
};
