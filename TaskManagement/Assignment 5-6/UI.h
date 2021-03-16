#pragma once
#include"Service.h"
#include"Validator.h"
#define MAX_PARAMETERS 5
#define MAX_CHARACTERS 250
#define PARAMETER_FIRST 0
#define PARAMETER_SECOND 1
#define PARAMETER_THIRD 2
#define PARAMETER_FOURTH 3
#define PARAMETER_FIFTH 4

class UI
{
private:
	Service service;
	Validator validator;
public:

	/*
		Constructor for the class UI
		Parameters:
			- service : Service
	*/
	//UI(Service& service) : service{ service } {};
	UI();

	/*
		Function for printing the menu
	*/
	void printMenu();

	/*
		Function for creating a Date object
		Parameters:
			- string : std::string
		Returns:
			- an object of class Date
	*/
	Date createDate(std::string string);

	/*
		Funtion for adding a task
		Parameters:
			- parameters : array of std::string
	*/
	void addTaskUI(std::vector<std::string> parameters);

	/*
		Funtion for updating a task
		Parameters:
			- parameters : array of std::string
	*/
	void updateTaskUI(std::vector<std::string> parameters);

	/*
		Funtion for deleting a task
		Parameters:
			- parameters : std::string
	*/
	void deleteTaskUI(std::string parameters);

	/*
		Funtion for printing the list of tasks
	*/
	void list();

	/*
		Function that starts the UI
	*/

	void nextUI();

	void saveUI(std::string title);

	void listFilteredUI(std::string type, std::string timesPerformed);

	void mylistUI();

	void openMylistUI();

	void runUI();

	~UI();
};

