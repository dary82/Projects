#include "UI.h"
#include <iostream>
#include <string>
#include "Utils.h"

using namespace std;

UI::UI()
{
}

void UI::printMenu()
{
	cout << "fileLocation ...\n";
	cout << "mylistLocation ...\n";
	cout << "mode X\n";
	cout << "exit\n";
	cout << "----------------------------------MODE-A-----------------------------------------\n";
	cout << "add title, type, lastPerformed(mm-dd-yyyy), timesPerformed, vision\n";
	cout << "update title, newType, newLastPerformed(dd-mm-yyyy), newTimesPerformed, newVision\n";
	cout << "delete title\n";
	cout << "list\n";
	cout << "---------------------------------------------------------------------------------\n";
	cout << "\n";
	cout << "----------------------------------MODE-B-----------------------------------------\n";
	cout << "next\n";
	cout << "save\n";
	cout << "list type, maximumTimesOperated\n";
	cout << "mylist\n";
	cout << "---------------------------------------------------------------------------------\n";
}

Date UI::createDate(std::string string)
{
	return validator.validateDate(string);
}

void UI::addTaskUI(std::vector<std::string> parameters)
{
	Date lastPerformed = this->createDate(parameters[PARAMETER_THIRD]);
	this->validator.validateParameters(parameters);
	bool success = this->service.addTaskService(parameters[PARAMETER_FIRST], parameters[PARAMETER_SECOND], lastPerformed, stoi(parameters[PARAMETER_FOURTH]), parameters[PARAMETER_FIFTH]);
	if (success)
		cout << "Added!\n";
	else
		cout << "No!\n";
}

void UI::updateTaskUI(std::vector<std::string> parameters)
{
	Date lastPerformed = this->createDate(parameters[PARAMETER_THIRD]);
	this->validator.validateParameters(parameters);
	bool success = this->service.updateTaskService(parameters[PARAMETER_FIRST], parameters[PARAMETER_SECOND], lastPerformed, stoi(parameters[PARAMETER_FOURTH]), parameters[PARAMETER_FIFTH]);
	if (success)
		cout << "Updated!\n";
	else
		cout << "No!\n";
}

void UI::deleteTaskUI(std::string parameters)
{
	this->validator.emptyString(parameters);
	bool success = this->service.deleteTaskService(parameters);
	if (success)
		cout << "Deleted!\n";
	else
		cout << "No!\n";
}

void UI::list()
{
	std::vector<Task> tasks = this->service.getAllTasksService();
	int size = tasks.size();
	if (size == 0)
		cout << "The list is empty\n";
	else
		for (auto i = tasks.begin(); i < tasks.end(); i++)
		{
			cout << i->getTitle() << ", " << i->getType() << ", " << i->getLastPerformedAsString() << ", " << i->getTimesPerformed() << ", " << i->getVision() << "\n";
		}
}

void UI::nextUI()
{
	Task task = this->service.nextService();
	cout << task.getTitle() << ", " << task.getType() << ", " << task.getLastPerformedAsString() << ", " << task.getTimesPerformed() << ", " << task.getVision() << "\n";
}

void UI::saveUI(std::string title)
{
	this->validator.emptyString(title);
	this->service.saveService(title);
}

void UI::listFilteredUI(std::string type, std::string timesPerformed)
{
	this->validator.emptyString(type);
	this->validator.validateInteger(timesPerformed);
	int maximumTimesPerformed = stoi(timesPerformed);
	std::vector<Task> tasks = this->service.getAllTasksService();
	int size = tasks.size();
	if (size == 0)
		cout << "The list is empty\n";
	else
		for (auto i = tasks.begin(); i < tasks.end(); i++)
		{
			if (i->getTimesPerformed()<maximumTimesPerformed && i->getType()==type)
				cout << i->getTitle() << ", " << i->getType() << ", " << i->getLastPerformedAsString() << ", " << i->getTimesPerformed() << ", " << i->getVision() << "\n";
		}
}

void UI::mylistUI()
{
	std::vector<Task> tasks = this->service.returnMyList();
	int size = tasks.size();
	if (size == 0)
		cout << "The list is empty\n";
	else
		for (auto i = tasks.begin(); i < tasks.end(); i++)
		{
			cout << i->getTitle() << ", " << i->getType() << ", " << i->getLastPerformedAsString() << ", " << i->getTimesPerformed() << ", " << i->getVision() << "\n";
		}
}

void UI::openMylistUI()
{
	this->service.openMyList();
}

void UI::runUI()
{
	this->printMenu();
	std::string mode="";
	std::vector<std::string> parameters;
	std::string command;
	std::string userInput;
	std::string fullPath = "";
	std::string myListPath = "";
	int positionOfNextSeparator;
	int parameterCount;
	while (true)
	{ 
		try
		{
			parameters.clear();
			cout << "Command: ";
			getline(cin, userInput);
			parameterCount = 0;
			positionOfNextSeparator = userInput.find(" ");
			if (positionOfNextSeparator == -1)
				command = userInput;
			else
				command = userInput.substr(0, positionOfNextSeparator);
			userInput = userInput.substr(positionOfNextSeparator + 1, userInput.length());
			while (parameterCount < 5)
			{
				positionOfNextSeparator = userInput.find(", ");
				if (positionOfNextSeparator == -1)
				{
					parameters.push_back(userInput);
					break;
				}
				parameters.push_back(userInput.substr(0, positionOfNextSeparator));
				userInput = userInput.substr(positionOfNextSeparator + 2, userInput.length());
				parameterCount++;
				/*cout << "Command: ";
			getline(cin, userInput);
			parameterCount = 0;
			positionOfNextSeparator = userInput.find(" ");
			if (positionOfNextSeparator == -1)
				command = userInput;
			else
				command = userInput.substr(0, positionOfNextSeparator);
			userInput = userInput.substr(positionOfNextSeparator + 1, userInput.length());
			while (parameterCount < 5)
			{
				positionOfNextSeparator = userInput.find(", ");
				if (positionOfNextSeparator == -1)
				{
					parameters[parameterCount] = userInput;
					break;
				}
				parameters[parameterCount] = userInput.substr(0, positionOfNextSeparator);
				userInput = userInput.substr(positionOfNextSeparator + 2, userInput.length());
				parameterCount++;*/
			}
			if (command == "exit")
			{
				break;
			}
			if (command == "fileLocation")
			{
				fullPath = parameters[0];
				this->validator.emptyString(fullPath);
				this->service.setPathService(fullPath);
				continue;
			}
			if (command == "mylistLocation")
			{
				myListPath = parameters[0];
				this->validator.emptyString(myListPath);
				this->service.setMyListPathService(myListPath);
				continue;
			}
			if (command == "mode")
			{
				mode = parameters[0];
				if (mode == "B")
					this->service.updateIteratorService();
				if (mode != "A" && mode != "B")
					cout << "Mode needs to be A or B\n";
				continue;
			}
			if (mode != "")
			{
				if (mode == "A")
				{
					if (command == "add")
						this->addTaskUI(parameters);
					else
						if (command == "update")
							this->updateTaskUI(parameters);
						else
							if (command == "delete")
								this->deleteTaskUI(parameters[PARAMETER_FIRST]);
							else
								if (command == "list")
									this->list();
								else
									cout << "Bad command\n";
				}
				else if (mode == "B")
				{
					if (command == "next")
						this->nextUI();
					else
						if (command == "save")
							this->saveUI(parameters[PARAMETER_FIRST]);
						else
							if (command == "list")
								if (parameters.size() == 2)
									this->listFilteredUI(parameters[PARAMETER_FIRST], parameters[PARAMETER_SECOND]);
								else
									cout << "Parameters for list filtered need to be 2\n";
							else
								if (command == "mylist")
									if (myListPath == "")
										this->mylistUI();
									else
										this->openMylistUI();
								else
									cout << "Bad command\n";
				}
				else
					cout << "Mode needs to be A or B\n";
			}
			else
				cout << "First select a mode (A OR B)\n";
		}
		catch (ValidatorException& Exception)
		{
			cout << "Something went wrong: " << Exception.what() << "\n";
		}
		catch (RepositoryException& Exception)
		{
			cout << "Something went wrong: " << Exception.what() << "\n";
		}
		catch (exception& Exception)
		{
			cout << "Something went wrong: " << Exception.what() << "\n";
		}
	}
}

UI::~UI()
{
}
