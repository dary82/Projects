#pragma once
#include "FileRepository.h"
#include "Validator.h"
#include "MyExceptions.h"
#include <fstream>

class HTMLRepository : public FileRepository
{
private:
	std::string fullPath;

	void createFile() override;
	std::vector<Task> loadFile() override;
	void saveFile(std::vector<Task> tasks) override;

public:
	HTMLRepository();
	HTMLRepository(std::string filePath);
	void openProgram() override;
	~HTMLRepository();
};

