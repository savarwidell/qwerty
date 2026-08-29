/*
Hace que se use el 100% del CPU calentando la PC/Laptop.

Savar Widell
*/


#include <vector>
#include <thread>

int main() {
	std::vector<std::thread> z;

	for (unsigned i = 0; i < std::thread::hardware_concurrency();i++) {
		z.emplace_back([] {
			volatile double x = 0;
			while (true) {
				x += 1.0;
			}
			});
	}

	for (auto& h : z) h.join();
}