#include "MemoryRepository.h"

bool MemoryRepository::add(const Task& newTask)
{
	for (auto i = this->tasks.begin(); i < this->tasks.end(); i++)
		if (newTask.getTitle() == i->getTitle())
			return false;
	this->tasks.push_back(newTask);
	return true;
}

bool MemoryRepository::update(const Task& newTask)
{
	for (auto i = this->tasks.begin(); i < this->tasks.end(); i++)
		if (newTask.getTitle() == i->getTitle())
		{
			i->setType(newTask.getType());
			i->setLastPerformed(newTask.getLastPerformed());
			i->setTimesPerformed(newTask.getTimesPerformed());
			i->setVision(newTask.getVision());

			return true;
		}
	return false;
}

bool MemoryRepository::deleteTask(std::string title)
{
	for (auto i = this->tasks.begin(); i < this->tasks.end(); i++)
		if (title == i->getTitle())
		{
			tasks.erase(i);
			return true;
		}
	return false;
}

std::vector<Task> MemoryRepository::getAllTasks()
{
	return this->tasks;
}

MemoryRepository::~MemoryRepository()
{
	;
}
