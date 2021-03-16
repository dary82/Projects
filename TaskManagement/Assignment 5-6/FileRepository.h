#pragma once
#include "Repository.h"
#include <fstream>

class FileRepository : public Repository
{
private:
	std::string fullPath;

	virtual void createFile();
	virtual std::vector<Task> loadFile();
	virtual void saveFile(std::vector<Task> tasks);

public:
	FileRepository();
	FileRepository(std::string filePath);
	virtual void setFullPath(std::string fullPath);
	virtual bool add(const Task& newTask) override;
	virtual bool update(const Task& newTask) override;
	virtual bool deleteTask(std::string title) override;
	virtual std::vector<Task> getAllTasks() override;
	virtual void openProgram() override;
	~FileRepository();

};

