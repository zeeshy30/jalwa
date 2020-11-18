import { schemaComposer } from 'graphql-compose';

schemaComposer.createObjectTC({
    name: 'Succeed',
    fields: { succeed: 'Boolean!' }
});
