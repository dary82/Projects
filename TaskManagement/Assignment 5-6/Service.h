#pragma once
#include "MemoryRepository.h"
#include "FileRepository.h"
#include "HTMLRepository.h"
#include <string>

class Service
{
private:
	Repository* repository;
	Repository* myListRepository;
	int position = 0;

public:

	/*
		Constructor for the class Service
	*/
	Service();

	/*
		Service function for adding an element
		Parameters:
			- title : std::string
			- type : std::string
			- lastPerformed : Date
			- timesPerformed : integer
			- vision : std::string
		Returns:
			- true, if it was added
			- false, otherwise
	*/
	bool addTaskService(std::string& title, std::string& type, Date lastPerformed, int timesPerformed, std::string& vision);

	/*
		Service function for updating an element
		Parameters:
			- title : std::string
			- newType : std::string
			- newLastPerformed : Date
			- newTimesPerformed : integer
			- newVision : std::string
		Returns:
			- true, if it was updated
			- false, otherwise
	*/
	bool updateTaskService(std::string& title, std::string& newType, Date newLastPerformed, int newTimesPerformed, std::string& newVision);

	/*
		Service function for deleting an element
		Parameters:
			- title : std::string
		Returns:
			- true, if it was deleted
			- false, otherwise
	*/
	bool deleteTaskService(std::string& title);

	/*
		Service function for getting all the elements
		Returns:
			- the list of elements (DynamicVector)
	*/
	std::vector<Task> getAllTasksService() const;

	void updateIteratorService();

	Task nextService();

	void saveService(std::string title);

	std::vector<Task> returnMyList();

	void openMyList();

	void setPathService(std::string fullPath);

	void setMyListPathService(std::string myListPath);

	~Service();
};

