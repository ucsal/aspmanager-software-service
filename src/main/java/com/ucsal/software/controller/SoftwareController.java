package com.ucsal.software.controller;

import com.ucsal.software.dto.request.CreateSoftwareRequest;
import com.ucsal.software.dto.request.CreateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSoftwareRequest;
import com.ucsal.software.dto.request.UpdateSolicitacaoSoftwareRequest;
import com.ucsal.software.dto.response.SoftwareResponse;
import com.ucsal.software.dto.response.SolicitacaoSoftwareResponse;
import com.ucsal.software.service.SoftwareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


public class SoftwareController {
}
