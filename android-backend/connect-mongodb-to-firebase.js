const admin = require('firebase-admin');
const serviceAccount = require('./android-backend-44de4-firebase-adminsdk-fbr4b-270dbeda96.json');
const { MongoClient, ObjectID } = require('mongodb');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const uri = 'mongodb+srv://RecipeUser:9WG8f1FO6xoRCMYh@cluster1.zibb76c.mongodb.net';

async function connectToMongoDB() {
  try {
    const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true });

    await client.connect();
    console.log('Connected to MongoDB');

    const db = client.db('recipe-api-db');
    const collection = db.collection('recipes');

    // Retrieve data from MongoDB collection
    const documents = await collection.find().toArray();

    // Modify the documents before storing in Firestore
    const modifiedDocuments = documents.map((document) => {
      // Convert ObjectId to string
      document._id = document._id.toString();
      return document;
    });

    console.log('Retrieved documents:', modifiedDocuments);

    // Initialize Firestore
    const firebaseDb = admin.firestore();

    // Write documents to Firestore
    for (const document of modifiedDocuments) {
      await firebaseDb.collection('recipes').add(document);
    }

    console.log('Data synchronized successfully');

    client.close();
  } catch (error) {
    console.error('Error:', error);
  }
}

connectToMongoDB();
