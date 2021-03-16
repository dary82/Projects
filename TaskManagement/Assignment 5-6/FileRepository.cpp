#include "FileRepository.h"
#include <iostream>
#include <fstream>
#include <Windows.h>
using namespace std;

void FileRepository::createFile()
{
	std::ofstream file(this->fullPath);
	file.close();
}

std::vector<Task> FileRepository::loadFile()
{
	std::vector<Task> tasks;
	ifstream file{ this->fullPath };
	if (!file.is_open())
	{
		this->createFile();
		return tasks;
	}
	Task task{};

	while (file >> task)
	{
		tasks.push_back(task);
	}
	file.close();

	return tasks;
}

void FileRepository::saveFile(std::vector<Task> tasks)
{
	std::ofstream file(this->fullPath);
	for (auto task : tasks)
	{
		file << task << endl;
	}

	file.close();
}

FileRepository::FileRepository()
{
	this->fullPath = "";
}

FileRepository::FileRepository(std::string fullPath)
{
	this->fullPath = fullPath;
	std::vector<Task> tasks;
	tasks=this->loadFile();
}

void FileRepository::setFullPath(std::string fullPath)
{
	this->fullPath = fullPath;
	std::vector<Task> tasks;
	tasks=this->loadFile();
}

bool FileRepository::add(const Task& newTask)
{
	std::vector<Task> tasks;
	tasks=this->loadFile();
	for (auto i = tasks.begin(); i < tasks.end(); i++)
		if (newTask.getTitle() == i->getTitle())
			return false;
	tasks.push_back(newTask);
	this->saveFile(tasks);
	return true;
}

bool FileRepository::update(const Task& newTask)
{
	std::vector<Task> tasks;
	tasks= this->loadFile();
	for (auto i=tasks.begin(); i < tasks.end(); i++)
		if (newTask.getTitle() == i->getTitle())
		{
			i->setType(newTask.getType());
			i->setLastPerformed(newTask.getLastPerformed());
			i->setTimesPerformed(newTask.getTimesPerformed());
			i->setVision(newTask.getVision());

			this->saveFile(tasks);
			return true;
		}
	return false;
}

bool FileRepository::deleteTask(std::string title)
{
	std::vector<Task> tasks;
	tasks=this->loadFile();
	for (auto i = tasks.begin(); i < tasks.end(); i++)
		if (title == i->getTitle())
		{
			tasks.erase(i);

			this->saveFile(tasks);
			return true;
		}
	return false;
}

std::vector<Task> FileRepository::getAllTasks()
{
	std::vector<Task> tasks;
	tasks=this->loadFile();

	return tasks;
}

void FileRepository::openProgram()
{
	ShellExecuteA(NULL, "open", fullPath.c_str(), NULL, NULL, SW_SHOWNORMAL);
}

FileRepository::~FileRepository()
{
}
