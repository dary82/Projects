#include "HTMLRepository.h"
#include <iostream>
#include <fstream>
#include <Windows.h>

using namespace std;

void HTMLRepository::createFile()
{
	std::ofstream file(this->fullPath);
	file.close();
}

std::vector<Task> HTMLRepository::loadFile()
{
	std::vector<Task> tasks;
	std::string line;
	std::vector<std::string> lineContent;
	ifstream file{ this->fullPath };
	if (!file.is_open())
	{
		this->createFile();
		return tasks;
	}

	for (int i = 0; i < 14; i++)
		getline(file, line);

	Task task{};

	while (true)
	{
		if (file.peek() == std::ifstream::traits_type::eof())
			break;

		lineContent.clear();
		getline(file, line);

		if (line == "</html>")
			break;

		if (line == "		<tr>")
		{
			for (int i = 0; i < 5; i++)
			{
				getline(file, line);
				line = line.substr(7, line.length() - 5);
				lineContent.push_back(line);
			}
			task.setTitle(lineContent[0]);
			task.setType(lineContent[1]);
			task.setLastPerformedFromString(lineContent[2]);
			task.setTimesPerformed(stoi(lineContent[3]));
			task.setVision(lineContent[4]);
			
			tasks.push_back(task);
		}
	}
	file.close();
	return tasks;
}

void HTMLRepository::saveFile(std::vector<Task> tasks)
{
	std::ofstream file(this->fullPath);
	file << "<!DOCTYPE html>\n";
	file << "<html>\n";
	file << "	<head>\n";
	file << "		<title>Magic</title>\n";
	file << "	</head>\n";
	file << "	<body>\n";
	file << "		<table border = \"1\">\n";
	file << "		<tr>\n";
	file << "			<td>Title</td>\n";
	file << "			<td>Type</td>\n";
	file << "			<td>Date</td>\n";
	file << "			<td>Times performed</td>\n";
	file << "			<td>Vision</td>\n";
	file << "		</tr>\n";
	for (auto task : tasks)
	{
		file << task.HTMLrepresentation();
	}
	file << "		</table>\n";
	file << "	</body>\n";
	file << "</html>";

	file.close();
}

HTMLRepository::HTMLRepository()
{
	this->fullPath = "";
}

HTMLRepository::HTMLRepository(std::string filePath)
{
	this->fullPath = filePath;
}

void HTMLRepository::openProgram()
{
	ShellExecuteA(NULL, "open", fullPath.c_str(), NULL, NULL, SW_SHOWNORMAL);
}

HTMLRepository::~HTMLRepository()
{
	;
}
