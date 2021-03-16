#include "Task.h"
#include "Utils.h"
#include <vector>

using namespace std;

Date::Date(int month, int day, int year)
{
	this->month = month;
	this->day = day;
	this->year = year;
}

int Date::getDay() const
{
	return this->day;
}

int Date::getMonth() const
{
	return this->month;
}

int Date::getYear() const
{
	return this->year;
}

bool Date::operator==(const Date& newDate)
{
	return (this->day == newDate.day and this->month == newDate.month and this->year == newDate.year);
}

Task::Task()
{
	this->title = "";
	this->type = "";
	this->lastPerformed = Date(0, 0, 0);
	this->timesPerformed = 0;
	this->vision = "";
}

Task::Task(const std::string& title, const std::string& type, Date lastPerformed, int timesPerformed, const std::string& vision)
{
	this->title = title;
	this->type = type;
	this->lastPerformed = lastPerformed;
	this->timesPerformed = timesPerformed;
	this->vision = vision;
}

std::string Task::getTitle() const
{
	return this->title;
}

void Task::setTitle(std::string newTitle)
{
	this->title = newTitle;
}

std::string Task::getType() const
{
	return this->type;
}

void Task::setType(std::string newType)
{
	this->type = newType;
}

Date Task::getLastPerformed() const
{
	return this->lastPerformed;
}

std::string Task::getLastPerformedAsString() const
{
	std::string monthString;
	std::string dayString;
	if (this->lastPerformed.getMonth() < 10)
	{
		monthString = "0" + std::to_string(this->lastPerformed.getMonth());
	}
	else
		monthString = std::to_string(this->lastPerformed.getMonth());

	if (this->lastPerformed.getDay() < 10)
	{
		dayString = "0" + std::to_string(this->lastPerformed.getDay());
	}
	else
		dayString = std::to_string(this->lastPerformed.getDay());

	std::string date = monthString + "-" + dayString + "-" + std::to_string(this->lastPerformed.getYear());
	return date;
}

void Task::setLastPerformed(Date newLastPerformed)
{
	this->lastPerformed = newLastPerformed;
}

void Task::setLastPerformedFromString(std::string newLastPerformed)
{
	std::string day = newLastPerformed.substr(3, 2);
	std::string month = newLastPerformed.substr(0, 2);
	std::string year = newLastPerformed.substr(6, 4);
	Date newDate{ stoi(month),stoi(day),stoi(year) };

	this->lastPerformed = newDate;
}

int Task::getTimesPerformed() const
{
	return this->timesPerformed;
}

void Task::setTimesPerformed(int newTimesPerformed)
{
	this->timesPerformed = newTimesPerformed;
}

std::string Task::getVision() const
{
	return this->vision;
}

void Task::setVision(std::string newVision)
{
	this->vision = newVision;
}

std::string Task::HTMLrepresentation() const
{
	std::string representation = "		<tr>\n";
	representation += "			<td>"+this->getTitle()+"</td>\n";
	representation += "			<td>" + this->getType() + "</td>\n";
	representation += "			<td>" + this->getLastPerformedAsString() + "</td>\n";
	representation += "			<td>" + std::to_string(this->getTimesPerformed()) + "</td>\n";
	representation += "			<td>" + this->getVision() + "</td>\n";
	representation += "		</tr>\n";

	return representation;
}

Task::Task(const Task& currentTask)
{
	this->title = currentTask.title;
	this->type = currentTask.type;
	this->lastPerformed = currentTask.lastPerformed;
	this->timesPerformed = currentTask.timesPerformed;
	this->vision = currentTask.vision;
}

bool Task::operator==(const Task& newTask)
{
	return (this->title == newTask.title and this->type == newTask.type and this->lastPerformed == newTask.lastPerformed and this->timesPerformed == newTask.timesPerformed and this->vision == newTask.vision);
}

Task& Task::operator=(const Task& currentTask)
{
	if (this != &currentTask)
	{
		this->title = currentTask.title;
		this->type = currentTask.type;
		this->lastPerformed = currentTask.lastPerformed;
		this->timesPerformed = currentTask.timesPerformed;
		this->vision = currentTask.vision;
	}
	return *this;
}

Task::~Task()
{
	;
}

std::istream& operator>>(std::istream& inputStream, Task& task)
{
	std::string line{};
	getline(inputStream, line);
	std::vector<std::string> tokens = tokenize(line, ',');

	if (tokens.size() != 5)
		return inputStream;

	task.title = tokens[PARAMETER_FIRST];
	task.type = tokens[PARAMETER_SECOND];
	task.setLastPerformedFromString(tokens[PARAMETER_THIRD]);
	task.timesPerformed = stoi(tokens[PARAMETER_FOURTH]);
	task.vision= tokens[PARAMETER_FIFTH];

	return inputStream;
}

std::ostream& operator<<(std::ostream& outputStream, Task & task)
{
	std::string separator = ",";
	outputStream << task.title << separator << task.type << separator << task.getLastPerformedAsString() << separator << std::to_string(task.timesPerformed) << separator << task.vision;
	return outputStream;
}
