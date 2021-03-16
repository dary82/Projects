#include "UI.h"
#include "Tests.h"
#include <crtdbg.h>

int main()
{
	{
		//TestAll();
		UI ui{};

		ui.runUI();
	}
	_CrtDumpMemoryLeaks();

	return 0;
}