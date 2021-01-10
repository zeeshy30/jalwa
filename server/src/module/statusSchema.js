import { schemaComposer } from 'graphql-compose';


export default schemaComposer.createObjectTC({
    name: 'status',
    fields: {
        statusCode: 'Int!',
        message: 'String!',
    }
});