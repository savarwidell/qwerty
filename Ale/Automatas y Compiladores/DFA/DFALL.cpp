/* M=(Q,Σ,δ,q0,F) */

#include "DFALL.hpp"

dfa::binary::BinaryAutomaton::BinaryAutomaton(const std::vector<State>& inputStates)
{
    states.resize(inputStates.size());

    for (const auto& state : inputStates)
    {
        states[state.id] = state;

        switch (state.type)
        {
        case StateType::INIT: initStateID = state.id; break;
        case StateType::INITF: initStateID = state.id; finalStatesIDs.push_back(state.id); break;
        case StateType::FIN: finalStatesIDs.push_back(state.id); break;
        case StateType::DEADPOINT: deadStateID = state.id; break;
        case StateType::INTER: break;
        }
    }
}

bool dfa::binary::BinaryAutomaton::isInputValid(std::string_view input)
{
    std::uint8_t currentStateID{ initStateID };
    bool isValid{ false };

    for (const auto& c : input)
    {
        if (currentStateID == deadStateID) break;
        if (c == '0') { currentStateID = states[currentStateID].if0; continue; }
        if (c == '1') { currentStateID = states[currentStateID].if1; continue; }

        currentStateID = deadStateID;
    }

    for (std::uint8_t finalID : finalStatesIDs)
    {
        if (currentStateID == finalID) { isValid = true; break; }   
    }

    return isValid;
}
