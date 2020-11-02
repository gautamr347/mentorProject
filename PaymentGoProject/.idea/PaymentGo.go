package main

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"io/ioutil"
	"log"
	"net/http"
)

type Payments struct {
	Id       string `json:"Id"`
	Tid      string `json:"Tid"`
	Amt      string `json:"Amt"`
	Txnid string `json:"txnid"`
	Remarks  string `json:"remarks"`
}

var collection *mongo.Collection

func init() {
	// connection to database
	fmt.Println("Connecting to mongo")
	clientOptions := options.Client().ApplyURI("mongodb://127.0.0.1:27017")
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
	}
	err = client.Ping(context.TODO(), nil)
	if err != nil {
		fmt.Println("error")
		log.Fatal(err)
	}
	fmt.Println("Connected to MongoDB!")
	collection = client.Database("mydb").Collection("payments")
}
func main() {
	myRouter := mux.NewRouter().StrictSlash(true)
	myRouter.HandleFunc("/createPayment", createPayment).Methods("POST")
	myRouter.HandleFunc("/getPayment/{id}", getPayment).Methods("GET")
	http.ListenAndServe(":7907", myRouter)
}
func createPayment(w http.ResponseWriter, req *http.Request) {
	fmt.Println("in createPayment")
	reqbody, _ := ioutil.ReadAll(req.Body)
	var payment Payments
	json.Unmarshal(reqbody, &payment)
	insertResult, err := collection.InsertOne(context.TODO(), payment)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Inserted a Single Document: ", insertResult.InsertedID)
	fmt.Println("Received object: ", payment)
}
func getPayment(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	idval := vars["id"]
	fmt.Println(idval)
	filter := bson.M{"id": idval}
	var result Payments
	err := collection.FindOne(context.TODO(), filter).Decode(&result)
	if err != nil {
		log.Fatal(err)
	}
	json.NewEncoder(w).Encode(result)
}



