# Finwise Development Guidelines

## Branching Strategy

We follow a modified GitFlow workflow:

- `main`: Production-ready code
- `develop`: Integration branch for ongoing development
- `feature/*`: New features or enhancements
- `bugfix/*`: Bug fixes
- `hotfix/*`: Urgent fixes for production
- `release/*`: Release preparation

### Branch Naming Convention

- Feature branches: `feature/FW-123-short-description`
- Bug fix branches: `bugfix/FW-123-short-description`
- Hotfix branches: `hotfix/FW-123-short-description`
- Release branches: `release/v1.0.0`

Where `FW-123` is the issue/ticket number from our issue tracker.

## Development Workflow

1. Create a new branch from `develop` (for features/bugfixes) or `main` (for hotfixes)
2. Implement changes with frequent commits
3. Write tests for your changes
4. Ensure all tests pass locally
5. Create a Pull Request targeting the appropriate branch
6. Address review comments
7. Merge once approved and CI passes

## Pull Request Guidelines

### PR Title Format
