#pragma once

#include <cstdint>
#include <vector>
#include <string_view>

namespace dfa {
	namespace binary {
		enum class StateType : std::uint8_t
		{
			INIT,
			INITF,
			FIN,
			DEADPOINT,
			INTER
		};

		struct State
		{
			std::uint8_t id;
			StateType type;
			std::uint8_t if0;
			std::uint8_t if1;
		};

		class BinaryAutomaton
		{
		private:
			std::vector<State> states{};
			std::vector<uint8_t> finalStatesIDs{};
			uint8_t initStateID{ 0 };
			uint8_t deadStateID{ 0 };

		public:
			BinaryAutomaton(const std::vector<State>& inputStates);
			bool isInputValid(std::string_view input);
		};
	}
}