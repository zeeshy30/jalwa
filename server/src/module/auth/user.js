import bcrypt from 'bcryptjs';

import mongoose from 'mongoose';

const { Schema } = mongoose;

const userSchema = new Schema(
    {
        phoneNumber: {
            type: String,
            required: true,
        },
        fullName: String,
        account: {
            verification: {
                verified: {
                    type: Boolean,
                    default: false
                },
                token: String,
            },
        }
    },
    { timestamps: true }
);

// eslint-disable-next-line func-names
userSchema.statics.numberExist = function(phoneNumber) {
    return this.findOne({ phoneNumber });
};

const User = mongoose.model('User', userSchema);

export default User;
