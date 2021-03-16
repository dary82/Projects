#pragma once
#include<string>
#include<iostream>
#define PARAMETER_FIRST 0
#define PARAMETER_SECOND 1
#define PARAMETER_THIRD 2
#define PARAMETER_FOURTH 3
#define PARAMETER_FIFTH 4

class Date
{
private:
	int month, day, year;

public:

	/*
		Constructor for the class Date
		Parameters:
			- month : integer
			- day : integer
			- year : integer
	*/
	Date(int month = 1, int day = 1, int year = 1);

	/*
		Getter for the field day
		Returns:
			- the day of the specified Date (integer)
	*/
	int getDay() const;

	/*
		Getter for the field month
		Returns:
			- the month of the specified Date (integer)
	*/
	int getMonth() const;

	/*
		Getter for the field year
		Returns:
			- the year of the specified Date (integer)
	*/
	int getYear() const;

	bool operator == (const Date& newDate);
};

class Task
{
private:
	std::string title;
	std::string type;
	Date lastPerformed;
	std::string lastPerformedString;
	int timesPerformed;
	std::string vision;

public:
	/*
		Constructor for class Task with no parameters
	*/
	Task();

	/*
		Constructor for class Task with parameters
		Parameters:
			- title : std::string
			- type : std::string
			- lastPerformed : Date
			- timesPerformed : integer
			- vision : std::string
	*/
	Task(const std::string& title, const std::string& type, Date lastPerformed, int timesPerformed, const std::string& vision);

	/*
		Getter for the field title
		Returns:
			- the title of the current task (std::string)
	*/
	std::string getTitle() const;

	/*
		Setter for the field title
		Parameters:
			- newTitle : std::string
	*/
	void setTitle(std::string newTitle);


	/*
		Getter for the field type
		Returns:
			- the type of the current task (std::string)
	*/
	std::string getType() const;

	/*
		Setter for the field type
		Parameters:
			- newType : std::string
	*/
	void setType(std::string newType);

	/*
		Getter for the field lastPerformed
		Returns:
			- the lastPerformed date of the current task (Date)
	*/
	Date getLastPerformed() const;

	/*
		Getter for the field lastPerformed
		Returns:
			- the lastPerformed date of the current task (std::string)
	*/
	std::string getLastPerformedAsString() const;

	/*
		Setter for the field lastPerformed
		Parameters:
			- newLastPerformed : Date
	*/
	void setLastPerformed(Date newLastPerformed);

	/*
		Getter for the field timesPerformed
		Returns:
			- the timesPerformed of the current task (integer)
	*/

	void setLastPerformedFromString(std::string newLastPerformed);

	int getTimesPerformed() const;

	/*
		Setter for the field timesPerformed
		Parameters:
			- newTimesPerformed : integer
	*/
	void setTimesPerformed(int newTimesPerformed);

	/*
		Getter for the field vision
		Returns:
			- the vision of the current task (std::string)
	*/
	std::string getVision() const;

	/*
		Setter for the field vision
		Parameters:
			- newVision : std::string
	*/
	void setVision(std::string newVision);

	std::string HTMLrepresentation() const;

	/*
		Copy constructor for the current Task
		Parameters:
			- currentTask : reference to an object of type Task
		Returns:
			- a copy of the given task (Task)
	*/
	Task(const Task& currentTask);

	bool operator == (const Task& newTask);

	Task& operator = (const Task& currentTask);

	friend std::istream& operator>>(std::istream& inputStream, Task& task);

	friend std::ostream& operator<<(std::ostream& outputStream, Task& task);

	/*
		Task destructor
	*/
	~Task();

};

