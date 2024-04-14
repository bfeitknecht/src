module ClockDivisor #(parameter ratio = 1) (
    input reset,
    input clk_in,
    output clk_out
    );
    
    reg [ratio-1:0] clk_count;
    always @ (posedge clk_in) begin
        if (reset) clk_count <= 0;
        else clk_count <= clk_count + 1;
    end
    assign clk_out = &clk_count;
endmodule

module FSM (
    input clk,
    input reset,
    input enable,
    output L, M, R
    );

    // state holding registers
    reg [2:0] state_p, state_n;
    reg [2:0] S0 = 3'b000;
    reg [2:0] S1 = 3'b001;
    reg [2:0] S2 = 3'b011;
    reg [2:0] S3 = 3'b111;
    
    // update state_ps based on inputs 
    always @ (posedge enable) begin
        case (state_p)
            S0: state_n <= S1;
            S1: state_n <= S2;
            S2: state_n <= S3;
            S3: state_n <= S0;
            default: state_n = S0;
        endcase
    end


    // next state_p logic
    always @ (posedge clk, posedge reset) begin
        if (reset) state_p <= S0;
        else state_p <= state_n;
    end

    // output logic
    assign {L, M, R} = state_n;
endmodule

module FORD(
    input clk_sys,
    input left,
    input right,
    input reset,
    output LA, LB, LC,
    output RA, RB, RC
    );

    // clock divisor
    wire clk;
    ClockDivisor #(.ratio(32)) clk_div (
        .reset(reset),
        .clk_in(clk_sys),
        .clk_out(clk)
        );

    // left state machine
    FSM LEFT (
        .clk(clk),
        .reset(reset),
        .enable(left),
        .L(LC),
        .M(LB),
        .R(LA)
        );

    // right state machine
    FSM RIGHT (
    .clk(clk),
    .reset(reset),
    .enable(right),
    .L(RA),
    .M(RB),
    .R(RC)
    );

endmodule