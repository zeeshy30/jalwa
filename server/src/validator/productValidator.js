import validator from 'validator';

class ProductValidator {
    addProduct = async (resolve, source, args, context, info) => {
        const { photoUrl, videoUrl, price } = args;

        if (!validator.isURL(videoUrl)) {
            return Promise.reject(new Error('Error: Invalid videoUrl provided'));
        }

        if (!validator.isURL(photoUrl)) {
            return Promise.reject(new Error('Error: Invalid photoUrl provided'));
        }

        if (!validator.isNumeric(price)) {
            return Promise.reject(new Error('Error: Invalid price provided'));
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
