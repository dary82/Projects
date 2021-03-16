#pragma once
#include"Task.h"
#include<vector>
#include<string>
#include<stdio.h>
#include<fstream>

class Repository
{
public:

	/*
		Constructor for the class Repository
	*/
	Repository() {};
	
	virtual bool add(const Task& newTask) = 0;

	virtual bool update(const Task& newTask) = 0;

	virtual bool deleteTask(std::string title) = 0;

	virtual std::vector<Task> getAllTasks() = 0;

	virtual void setFullPath(std::string fullPath) = 0;

	virtual void openProgram() = 0;

	virtual ~Repository();
};

