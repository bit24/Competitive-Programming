#include "branch.h"
#include <assert.h>
#include <iostream>

#include "trace.cc"
#include "predict.cc"

using namespace std;

// The code later in the file assumes power of 2 table sizes
#define BHT_SIZE 760
#define BTB_SIZE 897
#define MASK0 0b11111100
#define MASK1 0b11110011
#define MASK2 0b11001111
#define MASK3 0b00111111
#define PRIME1 1123
#define MASKF ~(1 << 28)

// You must put all of the state used by your predictor in this struct.
// You may not embed any pointers or STL data structures in here.
static struct
{
    uint8_t branch_history_table[BHT_SIZE]; // really only 2 bits per entry
    uint8_t exHist;
    uint32_t branch_target_buffer[BTB_SIZE];
    uint32_t cStack[16][2];
    uint32_t last;
} state;


// The total size of all the state used by your predictor must be smaller than 4KB.
// Feel free to comment this out for your own experimentation, but all final submissions
// must leave this check enabled.
static_assert(sizeof(state) <= 4096+256 + 300, "your state structure is too large!");


bool predict(uint32_t pc, uint32_t *target)
{
    // See if we have any record of this branch in the BTB
    uint32_t btb_index = pc % BTB_SIZE;
    //if (state.branch_target_buffer[btb_index].pc != pc)
    //    return false; // if the BTB doesn't remember a branch here, just predict "not taken"

    // Found it.  Ues the BHT to determine taken vs. not taken for this branch
    uint32_t bht_index = pc % BHT_SIZE;
    uint8_t bht = (state.branch_history_table[bht_index] >> (2 * state.exHist)) & 3;
    if (bht <= 1)
        return false; // the BHT predicts "not taken"

    // The BHT predicts "taken".  Use the BTB's predicted target address
    *target = state.branch_target_buffer[btb_index] & MASKF;
    return true;
}

void update(uint32_t pc, uint32_t target, bool taken, uint32_t flags)
{
    // Every time a branch is taken, remember it in the BTB.
    if (taken)
    {
        uint32_t btb_index = pc % BTB_SIZE;
        // state.branch_target_buffer[btb_index].pc = pc;
        state.branch_target_buffer[btb_index] = target;
    }

    // Every time a branch is either taken or not taken, update the 2-bit saturating counter in the BHT.
    uint32_t bht_index = pc % BHT_SIZE;
    uint8_t bht = (state.branch_history_table[bht_index] >> (2 * state.exHist)) & 3;


    if (taken)
    {
        if (bht < 3)
            bht++;
    }
    else
    {
        if (bht > 0)
            bht--;
    }

    if(state.exHist == 0){
        state.branch_history_table[bht_index] &= MASK0;
    }
    if(state.exHist == 1){
        state.branch_history_table[bht_index] &= MASK1;
    }
    if(state.exHist == 2){
        state.branch_history_table[bht_index] &= MASK2;
    }
    if(state.exHist == 3){
        state.branch_history_table[bht_index] &= MASK3;
    }

    state.branch_history_table[bht_index] |= bht << (2 * state.exHist);

    state.exHist <<= 1;
    state.exHist &= 3;
    if(taken){
        state.exHist |= 1;
    }


    if(flags & BR_CALL){
        cStack[last][0] = pc;
        cStack[last][1] = target;
        last++;
        last %= 16;
    }*/

}
