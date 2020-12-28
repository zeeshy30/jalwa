import validator from 'validator';

class CategoryValidator {
    addCategory = async (resolve, source, args, context, info) => {
        return resolve(source, args, context, info);
    }

    static getInstance () {
        if (!this.instance) {
            this.instance = new this();
        }
        return this.instance;
    }
}

export default CategoryValidator;
