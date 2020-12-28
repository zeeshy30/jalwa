import mongoose from 'mongoose';

const { Schema } = mongoose;

const productSKUSchema = new Schema(
    {
        SKU: {
            type: String,
            required: true,
        },
        handle: {
            type: String,
            required: true,
        },
        variantType1: {
            type: String,
            required: true,
        },
        variant1: {
            type: String,
            required: true,
        },
        variantType2: String,
        variant2: String,
        quantity: {
            type: Number,
            required: true,
        }
    },
    { timestamps: true }
);

const ProductSKU = mongoose.model('ProductSKU', productSKUSchema);

export default ProductSKU;
