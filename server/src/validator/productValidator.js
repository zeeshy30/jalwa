import validator from 'validator';

class ProductValidator {
    addProduct = async (resolve, source, args, context, info) => {
        const { photoUrl, videoUrl, price } = args;

        if (!validator.isURL(videoUrl)) {

            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Error: Invalid Video Url'
            };
        }

        if (!validator.isURL(photoUrl)) {
            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Error: Invalid Image Url'
            };
        }

        if (!validator.isNumeric(price)) {
            return {
                __typename: 'Error',
                statusCode: 422,
                message: 'Error: Price should be a Number'
            };
        }

        return resolve(source, args, context, info);
    }

    static getInstance () {
        if (!this.instance) {
            this.instance = new this();
        }
        return this.instance;
    }
}

export default ProductValidator;
