package main

import (
	"fmt"
	"net/http"
	"github.com/gorilla/mux"
	"encoding/json"
	"io/ioutil"
	"context"
	"log"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type Person struct {
	Id      int64 `json:"Id"`
	TransactionId  int64   `json:"TransactionId"`
	Amount    float32 `json:"Amount"`
	TransacionId string `json:"TransacionId"`
	Remarks string `json:"remarks"`
}

var Allpeople []Person

func Allpersons(w http.ResponseWriter, req *http.Request) {
	fmt.Println("sending all persons")
	json.NewEncoder(w).Encode(Allpeople)
}

func homelink(w http.ResponseWriter, req *http.Request) {
	fmt.Fprintf(w, "My first Go Rest API")
}
func GetPerson(w http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	idval := vars["id"]
	for _, person1 := range Allpeople {
		if person1.Id == idval {
			json.NewEncoder(w).Encode(person1)
		}
	}
}

func createPerson(w http.ResponseWriter, req *http.Request) {
	fmt.Printf("in createPerson")
	reqbody, _ := ioutil.ReadAll(req.Body)
	var pperson Person
	json.Unmarshal(reqbody, &pperson)
	fmt.Println("Received object ", pperson)
	Allpeople = append(Allpeople, pperson)
	fmt.Fprintf(w, "Hello")
	clientOptions := options.Client().ApplyURI("mongodb://127.0.0.1:27017")
	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		log.Fatal(err)
		}
	err = client.Ping(context.TODO(), nil)
	if err != nil {
		log.Fatal(err)
		}
	fmt.Println("Connected to MongoDB!")
	collection := client.Database("mydb").Collection("personal")
	ruan := pperson
	insertResult, err := collection.InsertOne(context.TODO(), ruan)
	if err != nil {
		log.Fatal(err)
		}
	fmt.Println("Inserted a Single Document: ", insertResult.InsertedID)
}

func main() {
	Allpeople = []Person{
		Person{Id: "1", Name: "kumar", City: "patna", Address: "address111"},
		Person{Id: "2", Name: "kumar1", City: "patna1", Address: "address222"}}
	myRouter := mux.NewRouter().StrictSlash(true)
	myRouter.HandleFunc("/", homelink)
	myRouter.HandleFunc("/getpayment/{id}", GetPerson)
	/*myRouter.HandleFunc("/updateperson/{id}", UpdatePerson).Methods("POST")*/
	myRouter.HandleFunc("/createperson", createPerson).Methods("POST")
	myRouter.HandleFunc("/allpersons", Allpersons)
	http.ListenAndServe(":9828", myRouter)
}
