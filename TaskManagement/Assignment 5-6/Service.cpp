#include "Service.h"

Service::Service()
{
	this->repository = new MemoryRepository();
	this->myListRepository = new MemoryRepository();
}

bool Service::addTaskService(std::string& title, std::string& type, Date lastPerformed, int timesPerformed, std::string& vision)
{
	Task newTask = Task(title, type, lastPerformed, timesPerformed, vision);
	return this->repository->add(newTask);
}

bool Service::updateTaskService(std::string& title, std::string& newType, Date newLastPerformed, int newTimesPerformed, std::string& newVision)
{
	Task newTask = Task(title, newType, newLastPerformed, newTimesPerformed, newVision);
	return this->repository->update(newTask);
}

bool Service::deleteTaskService(std::string& title)
{
	return this->repository->deleteTask(title);
}

std::vector<Task> Service::getAllTasksService() const
{
	return this->repository->getAllTasks();
}

void Service::updateIteratorService()
{
	this->position = 0;
}

Task Service::nextService()
{
	std::vector<Task> tasks = this->repository->getAllTasks();
	if (tasks.size()==0)
		throw std::exception("There is nothing to show");
	if (this->position == tasks.size()-1)
		this->position = 0;
	else
		this->position++;
	return tasks.at(this->position);
}

void Service::saveService(std::string title)
{
	std::vector<Task> tasks = this->repository->getAllTasks();
	if (tasks.size() == 0)
		throw std::exception("There is nothing to show");
	Task task;
	for (auto i : tasks)
	{
		if (i.getTitle() == title)
			task = i;
	}
	std::vector<Task>myList = this->myListRepository->getAllTasks();
	this->myListRepository->add(task);
}

std::vector<Task> Service::returnMyList()
{
	return this->myListRepository->getAllTasks();
}

void Service::openMyList()
{
	this->myListRepository->openProgram();
}

void Service::setPathService(std::string fullPath)
{
	delete this->repository;
	this->repository = new FileRepository(fullPath);
}

void Service::setMyListPathService(std::string myListPath)
{
	int dot = myListPath.find(".");
	std::string extension = myListPath.substr(dot + 1, myListPath.length());
	if (extension == "txt")
	{
		delete this->myListRepository;
		this->myListRepository = new FileRepository(myListPath);
		return;
	}
	if (extension == "csv")
	{
		delete this->myListRepository;
		this->myListRepository = new FileRepository(myListPath);
		return;
	}
	if (extension == "html")
	{
		delete this->myListRepository;
		this->myListRepository = new HTMLRepository(myListPath);
		return;
	}
}

Service::~Service()
{
	delete this->repository;
	delete this->myListRepository;
}
