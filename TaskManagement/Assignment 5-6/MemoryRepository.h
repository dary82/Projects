#pragma once
#include "Repository.h"
#include<vector>
#include<string>

class MemoryRepository : public Repository
{
private:
	std::vector<Task> tasks;

public:
	MemoryRepository() {};
	bool add(const Task& newTask) override;

	bool update(const Task& newTask) override;

	bool deleteTask(std::string title) override;

	std::vector<Task> getAllTasks() override;

	virtual void setFullPath(std::string fullPath) {};

	virtual void openProgram() {};

	~MemoryRepository();
};

