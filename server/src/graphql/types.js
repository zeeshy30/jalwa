import { schemaComposer } from 'graphql-compose';

const succeed = schemaComposer.createObjectTC({
    name: 'Succeed',
    fields: { succeed: 'Boolean!' }
});

const error = schemaComposer.createObjectTC({
    name: 'Error',
    fields: {
        statusCode: 'Int!',
        message: 'String!',
    }
});

schemaComposer.createUnionTC({
    name: 'Status',
    types: [ error, succeed ]
});

export { error, succeed };